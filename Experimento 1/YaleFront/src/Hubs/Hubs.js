import React, { Component } from 'react';
import { Table, Button, FormGroup, ControlLabel, FormControl, Glyphicon } from 'react-bootstrap';
import { API_URL } from './../constants';
import Hub from './Hub';
import axios from 'axios';

class Hubs extends Component {
  componentWillMount() {
    this.setState({ 
      hubs: [],
      message: '',
      name: '',
      code: ''
    });
  }
  componentDidMount() {
    this.getHubs();
  }
  handleNameChange(event) {
    this.setState({ name: event.target.value });
  }
  handleCodeChange(event) {
    this.setState({ code: event.target.value });
  }
  addHub(event) {
    event.preventDefault();
    const { getIdToken } = this.props.auth;
    const headers = { Authorization: `Bearer ${getIdToken()}`};
    const hub = { name: this.state.name, code: this.state.code };
    axios.post(`${API_URL}/hubs`, hub, { credentials: true, headers: headers })
    .then(response => this.getHubs())
    .catch(error => this.setState({ message: error.message }));
  }
  getHubs() {
    const { getIdToken } = this.props.auth;
    const headers = { Authorization: `Bearer ${getIdToken()}`};
    axios.get(`${API_URL}/hubs`, { credentials: true, headers: headers })
    .then(response => this.setState({ hubs: response.data }))
    .catch(error => this.setState({ message: error.message }));
  }
  render() {
    const { isAuthenticated } = this.props.auth;
    return (
      <div className="container">
      <h1>Hubs</h1>
      <h2>Add hub</h2>
      <form onSubmit={(event) => this.addHub(event)}>
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
          {this.state.hubs.map((hub, index) => {
          return (
            <Hub
              key={index}
              number={index + 1}
              id={hub.id}
              name={hub.name}
              code={hub.code}
              auth={this.props.auth}
              Hubs={() => this.getHubs()}
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

export default Hubs;
