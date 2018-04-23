import React, { Component } from 'react';
import { Table, Button, FormGroup, ControlLabel, FormControl, Glyphicon } from 'react-bootstrap';
import { API_URL } from './../constants';
import Alarma from './Alarma';
import axios from 'axios';

class Alarmas extends Component {
  componentWillMount() {
    this.setState({ 
      alarmas: [],
      message: '',
      name: '',
      code: ''
    });
  }
  componentDidMount() {
    this.getAlarmas();
  }
  handleNameChange(event) {
    this.setState({ name: event.target.value });
  }
  handleCodeChange(event) {
    this.setState({ code: event.target.value });
  }
  addAlarma(event) {
    event.preventDefault();
    const { getIdToken } = this.props.auth;
    const headers = { Authorization: `Bearer ${getIdToken()}`};
    const alarma = { name: this.state.name, code: this.state.code };
    axios.post(`${API_URL}/alarmas`, alarma, { credentials: true, headers: headers })
    .then(response => this.getAlarmas())
    .catch(error => this.setState({ message: error.message }));
  }
  getAlarmas() {
    const { getIdToken } = this.props.auth;
    const headers = { Authorization: `Bearer ${getIdToken()}`};
    axios.get(`${API_URL}/alarmas`, { credentials: true, headers: headers })
    .then(response => this.setState({ alarmas: response.data }))
    .catch(error => this.setState({ message: error.message }));
  }
  render() {
    const { isAuthenticated } = this.props.auth;
    return (
      <div className="container">
      <h1>Alarmas</h1>
      <h2>Add alarma</h2>
      <form onSubmit={(event) => this.addAlarma(event)}>
        <FormGroup controlId="formInlineName">
          <ControlLabel>Name</ControlLabel>
          {' '}
          <FormControl type="text" placeholder="Name" onChange={(event) => this.handleNameChange(event)} />
        </FormGroup>
        {' '}
        <FormGroup controlId="formInlineCode">
          <ControlLabel>Code</ControlLabel>
          {' '}
          <FormControl type="text" placeholder="Code" onChange={(event) => this.handleCodeChange(event)} />
        </FormGroup>
        {' '}
        <Button bsStyle="success" type="submit">
          <Glyphicon glyph="plus" /> Add
        </Button>
      </form>
      <br />
      <Table striped bordered condensed hover className="center">
        <thead>
          <tr>
            <th>#</th>
            <th>ID</th>
            <th>Name</th>
            <th>Code</th>
            <th>Delete</th>
            <th>Edit</th>
          </tr>
        </thead>
        <tbody>
          {this.state.alarmas.map((alarma, index) => {
          return (
            <Alarma
              key={index}
              number={index + 1}
              id={alarma.id}
              name={alarma.name}
              code={alarma.code}
              auth={this.props.auth}
              Alarmas={() => this.getAlarmas()}
            />
          );
        })}
        </tbody>
      </Table>
      <h2>{this.state.message}</h2>
      </div>
      );
  }
}

export default Alarmas;
