import React, { Component } from 'react';
import { Table, Button, FormGroup, ControlLabel, FormControl, Glyphicon } from 'react-bootstrap';
import { API_URL } from './../constants';
import Inmueble from './Inmueble';
import axios from 'axios';

class Inmuebles extends Component {
  componentWillMount() {
    this.setState({ 
      inmuebles: [],
      message: '',
      name: '',
      code: ''
    });
  }
  componentDidMount() {
    this.getInmuebles();
  }
  handleNameChange(event) {
    this.setState({ name: event.target.value });
  }
  handleCodeChange(event) {
    this.setState({ code: event.target.value });
  }
  addInmueble(event) {
    event.preventDefault();
    const { getIdToken } = this.props.auth;
    const headers = { Authorization: `Bearer ${getIdToken()}`};
    const inmueble = { name: this.state.name, code: this.state.code };
    axios.post(`${API_URL}/inmuebles`, inmueble, { credentials: true, headers: headers })
    .then(response => this.getInmuebles())
    .catch(error => this.setState({ message: error.message }));
  }
  getInmuebles() {
    const { getIdToken } = this.props.auth;
    const headers = { Authorization: `Bearer ${getIdToken()}`};
    axios.get(`${API_URL}/inmuebles`, { credentials: true, headers: headers })
    .then(response => this.setState({ inmuebles: response.data }))
    .catch(error => this.setState({ message: error.message }));
  }
  render() {
    const { isAuthenticated } = this.props.auth;
    return (
      <div className="container">
      <h1>Inmuebles</h1>
      <h2>Add inmueble</h2>
      <form onSubmit={(event) => this.addInmueble(event)}>
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
          {this.state.inmuebles.map((inmueble, index) => {
          return (
            <Inmueble
              key={index}
              number={index + 1}
              id={inmueble.id}
              name={inmueble.name}
              code={inmueble.code}
              auth={this.props.auth}
              Inmuebles={() => this.getInmuebles()}
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

export default Inmuebles;
