import React, { Component } from 'react';
import { Button, Glyphicon, Modal, FormControl, FormGroup, ControlLabel } from 'react-bootstrap';
import { API_URL } from './../constants';
import axios from 'axios';

class Inmueble extends Component {
	componentWillMount() {
	    this.setState({ 
	      showModal: false,
	      name: '',
	      code: ''
	    });
	 }
	handleNameChange(event) {
    	this.setState({ name: event.target.value });
  	}
  	handleCodeChange(event) {
    	this.setState({ code: event.target.value });
  	}
	deleteInmueble() {
		const { getIdToken } = this.props.auth;
	    const headers = { Authorization: `Bearer ${getIdToken()}`};
	    axios.delete(`${API_URL}/inmuebles/${this.props.id}`, { credentials: true, headers: headers })
		    .then(response => { 
		    	console.log("Deleted successfully");
		    	this.props.getInmuebles();
		    });
	}
	updateInmueble() {
		const { getIdToken } = this.props.auth;
		const inmueble = { id: this.props.id, name: this.state.name, code: this.state.code };
	    const headers = { Authorization: `Bearer ${getIdToken()}`};
	    axios.put(`${API_URL}/inmuebles`, inmueble, { credentials: true, headers: headers })
		    .then(response => { 
		    	console.log("Updated successfully");
		    	this.close();
		    	this.props.getInmuebles();
		    });
	}
	close() {
	    this.setState({ showModal: false });
	}

	open() {
	    this.setState({ showModal: true });
	}
	render() {
		return (
			<tr>
				<td>{this.props.number}</td>
				<td>{this.props.id}</td>
				<td>{this.props.name}</td>
				<td>{this.props.code}</td>
				<td>
					<Button
                    bsStyle="danger"
                    className="btn-margin"
                    onClick={() => this.deleteInmueble()}>
                    	<Glyphicon glyph="remove" /> Delete
                	</Button>
                </td>
               	<td>
					<Button
                    bsStyle="info"
                    className="btn-margin"
                    onClick={() => this.open()}>
                    	<Glyphicon glyph="edit" /> Edit
                	</Button>
                	<Modal show={this.state.showModal} onHide={() => this.close()}>
			          <Modal.Header closeButton>
			            <Modal.Title>Edit inmueble</Modal.Title>
			          </Modal.Header>
			          <Modal.Body>
			            <form>
					        <FormGroup controlId="formInlineName">
					          <ControlLabel>Name</ControlLabel>
					          {' '}
					          <FormControl type="text" placeholder={this.props.name} onChange={(event) => this.handleNameChange(event)} />
					        </FormGroup>
					        {' '}
					        <FormGroup controlId="formInlineCode">
					          <ControlLabel>Code</ControlLabel>
					          {' '}
					          <FormControl type="text" placeholder={this.props.code} onChange={(event) => this.handleCodeChange(event)} />
					        </FormGroup>
					     </form>
			          </Modal.Body>
			          <Modal.Footer>
			          	<Button 
			          		bsStyle="info"
		                    className="btn-margin"
		                    onClick={(event) => this.updateInmueble(event)}>
		                    <Glyphicon glyph="edit" /> Save
                    	</Button>
			            <Button onClick={() => this.close()}>Close</Button>
			          </Modal.Footer>
			        </Modal>
                </td>
			</tr>

		);
	}
}
export default Inmueble;