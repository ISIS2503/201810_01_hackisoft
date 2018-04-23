import React, { Component } from 'react';
import { Table, Button, FormGroup, ControlLabel, FormControl, Glyphicon } from 'react-bootstrap';
import { API_URL } from './../constants';
import UnidadResidencial from './UnidadResidencial';
import axios from 'axios';

class UnidadesResidenciales extends Component {
  componentWillMount() {
    this.setState({ 
      unidadesResidenciales: [],
      message: '',
      name: '',
      code: ''
    });
  }
  componentDidMount() {
    this.getUnidadesResidenciales();
  }
  handleNameChange(event) {
    this.setState({ name: event.target.value });
  }
  handleCodeChange(event) {
    this.setState({ code: event.target.value });
  }
  addUnidadResidencial(event) {
    event.preventDefault();
    const { getIdToken } = this.props.auth;
    const headers = { Authorization: `Bearer ${getIdToken()}`};
    const unidadResidencial = { name: this.state.name, code: this.state.code };
    axios.post(`${API_URL}/unidadesResidenciales`, unidadResidencial, { credentials: true, headers: headers })
    .then(response => this.getUnidadesResidenciales())
    .catch(error => this.setState({ message: error.message }));
  }
  getUnidadesResidenciales() {
    const { getIdToken } = this.props.auth;
    const headers = { Authorization: `Bearer ${getIdToken()}`};
    axios.get(`${API_URL}/unidadesResidenciales`, { credentials: true, headers: headers })
    .then(response => this.setState({ unidadesResidenciales: response.data }))
    .catch(error => this.setState({ message: error.message }));
  }
  render() {
    const { isAuthenticated } = this.props.auth;
    return (
      <div className="container">
      <h1>UnidadesResidenciales</h1>
      <h2>Add unidadResidencial</h2>
      <form onSubmit={(event) => this.addUnidadResidencial(event)}>
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
          {this.state.unidadesResidenciales.map((unidadResidencial, index) => {
          return (
            <UnidadResidencial
              key={index}
              number={index + 1}
              id={unidadResidencial.id}
              name={unidadResidencial.name}
              code={unidadResidencial.code}
              auth={this.props.auth}
              UnidadesResidenciales={() => this.getUnidadesResidenciales()}
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

export default UnidadesResidenciales;
