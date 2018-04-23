import React, { Component } from 'react';
import { Button, Table, Form, FormGroup, FormControl, ControlLabel } from 'react-bootstrap';
import { API_URL } from './../constants';
import axios from 'axios';

class Rooms extends Component {
  componentWillMount() {
    this.setState({ 
      data: [],
      room: '',
      message: ''
    });
  }

  getConsolidatedData(event) {
    event.preventDefault();
    const { getIdToken } = this.props.auth;
    const headers = { Authorization: `Bearer ${getIdToken()}`};
    axios.get(`${API_URL}/rooms/${this.state.room}/consolidateddata`, { credentials: true, headers: headers })
    .then(response => this.setState({ message: '', data: response.data }))
    .catch(error => this.setState({ message: error.message, data: [] }));
  }

  handleSearchChange(event) {
    this.setState({ room: event.target.value });
  }

  render() {
    const { isAuthenticated } = this.props.auth;
    return (
      <div className="container">
      <h1>Consolidated data</h1>
      <Form inline onSubmit={(event) => this.getConsolidatedData(event)}>
        <FormGroup controlId="formInlineName">
          <ControlLabel>Room code</ControlLabel>
          {' '}
          <FormControl type="text" placeholder="piso1.local1"  onChange={(event) => this.handleSearchChange(event)} />
        </FormGroup>
        {' '}
        <Button type="submit">
          Get historical data
        </Button>
      </Form>
      <Table striped bordered condensed hover>
        <thead>
          <tr>
            <th>#</th>
            <th>ID</th>            
            <th>Value</th>
            <th>Date start</th>
            <th>Date end</th>
            <th>Room ID</th>
          </tr>
        </thead>
        <tbody>
          {this.state.data.map((data, index) => {
          return <tr key={index}>
            <td>{index}</td>
            <td>{data.id + 1}</td>            
            <td>{data.dataValue}</td>
            <td>{data.dateInit}</td>
            <td>{data.dateEnd}</td>
            <td>{data.roomID}</td>
          </tr>
        })}
        </tbody>
      </Table>
      <h2>{this.state.message}</h2>
      </div>
      );
  }
}

export default Rooms;
