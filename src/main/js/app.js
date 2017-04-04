'use strict';

const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');

class ShoppingList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {lists: []};
    }

    componentDidMount() {
        client({method: 'GET', path: '/v1/api'}).done(response => {
            this.setState({lists: response.entity});
        });
    }

    render() {
        return (
            <ItemsLists lists={this.state.lists}/>
        )
    }
}

class ItemsLists extends React.Component {
    render() {
        const lists = this.props.lists.map(list =>
            <ItemsList list={list}/>
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
    render() {
        const listId = this.props.list.id;
        return (
            <li>
                <p>
                    <a href={'/react/itemsList?id=' + listId} className="btn btn-default btn-lg">{this.props.list.name}</a>&nbsp;
                    <a href={'/react/itemsList/edit?id=' + listId} className="btn btn-warning btn-xs">Edit</a>&nbsp;
                    <a href={'/react/itemsList/delete?id=' + listId} className="btn btn-danger btn-xs"
                       onClick="return confirm('Are you sure you want to delete this list?')">Delete</a>
                </p>
            </li>
        )
    }
}

ReactDOM.render(
    <ShoppingList/>,
    document.getElementById('react')
);