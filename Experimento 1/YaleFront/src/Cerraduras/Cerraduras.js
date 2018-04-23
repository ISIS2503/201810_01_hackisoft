import React, { Component } from 'react';
import { Table, Button, FormGroup, ControlLabel, FormControl, Glyphicon } from 'react-bootstrap';
import { API_URL } from './../constants';
import Cerradura from './Cerradura';
import axios from 'axios';

class Cerraduras extends Component {
  componentWillMount() {
    this.setState({ 
      cerraduras: [],
      message: '',
      name: '',
      code: ''
    });
  }
  componentDidMount() {
    this.getCerraduras();
  }
  handleNameChange(event) {
    this.setState({ name: event.target.value });
  }
  handleCodeChange(event) {
    this.setState({ code: event.target.value });
  }
  addCerradura(event) {
    event.preventDefault();
    const { getIdToken } = this.props.auth;
    const headers = { Authorization: `Bearer ${getIdToken()}`};
    const cerradura = { name: this.state.name, code: this.state.code };
    axios.post(`${API_URL}/cerraduras`, cerradura, { credentials: true, headers: headers })
    .then(response => this.getCerraduras())
    .catch(error => this.setState({ message: error.message }));
  }
  getCerraduras() {
    const { getIdToken } = this.props.auth;
    const headers = { Authorization: `Bearer ${getIdToken()}`};
    axios.get(`${API_URL}/cerraduras`, { credentials: true, headers: headers })
    .then(response => this.setState({ cerraduras: response.data }))
    .catch(error => this.setState({ message: error.message }));
  }
  render() {
    const { isAuthenticated } = this.props.auth;
    return (
      <div className="container">
      <h1>Cerraduras</h1>
      <h2>Add cerradura</h2>
      <form onSubmit={(event) => this.addCerradura(event)}>
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
          {this.state.cerraduras.map((cerradura, index) => {
          return (
            <Cerradura
              key={index}
              number={index + 1}
              id={cerradura.id}
              name={cerradura.name}
              code={cerradura.code}
              auth={this.props.auth}
              Cerraduras={() => this.getCerraduras()}
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

export default Cerraduras;
