'use strict';

const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');
import {Router, Route, Link, Redirect, browserHistory, IndexRoute} from 'react-router';

class ShoppingList extends React.Component {
    constructor(props) {
        super(props);
        this.openList = this.openList.bind(this);
        this.editList = this.editList.bind(this);
        this.deleteList = this.deleteList.bind(this);

        this.state = {lists: [], list: {}};
    }

    componentDidMount() {
        client({method: 'GET', path: '/v1/api'}).done(response => {
            this.setState({lists: response.entity});
        });
    }

    openList(list) {
        client({method: 'GET', path: '/v1/api/itemsList/' + list.id}).done(response => {
            this.setState({list: response.entity});
        });
        console.log('openList ' + list.id);
    }

    editList(list) {
        console.log('editList ');
    }

    deleteList(list) {
        console.log('deleteList ');
    }

    render() {
        return (
            <ItemsLists lists={this.state.lists}
                        list={this.state.list}
                        openList={this.openList}
                        editList={this.editList}
                        deleteList={this.deleteList}/>
        )
    }
}

class ItemsLists extends React.Component {
    render() {
        const lists = this.props.lists.map(list =>
            <ItemsList key={list.id}
                       list={list}
                       openList={this.props.openList}
                       editList={this.props.editList}
                       deleteList={this.props.deleteList}/>
        );
        return (
            <div className="col-md-6 col-md-offset-3">
                <h1>Shopping list</h1>
                <ul className="list-unstyled">
                    {lists}
                </ul>
                <a href="/react/itemsList/add" className="btn btn-success btn-xs" data-toggle="tooltip" data-placement="right" title="Add new list">
                    <span className="glyphicon glyphicon-plus"/>
                </a>
            </div>
        )
    }
}

class ItemsList extends React.Component {
    constructor(props) {
        super(props);
        this.openList = this.openList.bind(this);
        this.editList = this.editList.bind(this);
        this.deleteList = this.deleteList.bind(this);
    }

    openList() {
        this.props.openList(this.props.list);
    }

    editList() {
        this.props.editList(this.props.list);
    }

    deleteList() {
        this.props.deleteList(this.props.list);
    }

    render() {
        return (
            <li>
                <p>
                    {/*<Link to="/react/itemsList" className="btn btn-default btn-lg">{this.props.list.name}</Link>&nbsp;*/}
                    <a href="#" onClick={this.openList} className="btn btn-default btn-lg">{this.props.list.name}</a>&nbsp;
                    <a href="#" onClick={this.editList} className="btn btn-warning btn-xs">Edit</a>&nbsp;
                    <a href="#" onClick={this.deleteList} className="btn btn-danger btn-xs">Delete</a>
                </p>
            </li>
        )
    }
}

class Items extends React.Component {
    constructor(props) {
        super(props);
        console.log('list ' + this.props.list);
    }
    render() {
        return (
            <h1>{this.props.list.name}</h1>
        )
    }
}

ReactDOM.render(
    <ShoppingList/>,
    document.getElementById('react')
);