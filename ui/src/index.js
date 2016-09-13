import React from 'react';
import ReactDOM from 'react-dom';
// import App from './App';
import './index.css';
import Auth0Lock from 'auth0-lock';


var App = React.createClass({
  // ...
  componentWillMount: function() {
      this.lock = new Auth0Lock('IGFzIUYJOkGqTKAChSynSXFjxVDJreTE', 'surajsongire1.auth0.com');
  },
  render: function() {
    return (<Home lock={this.lock}/>);
  }
});

var Home = React.createClass({
  // ...
  showLock: function() {
    // Show the Auth0Lock widget
    this.props.lock.show();
  },

  render: function() {
    return (
    <div className="login-box">
      <a onClick={this.showLock}>Sign In</a>
    </div>);
  }
});
ReactDOM.render(
  <App />,
  document.getElementById('root')
);
