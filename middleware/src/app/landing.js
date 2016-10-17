import React, { Component } from 'react';
import Auth0Lock from 'auth0-lock';


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

var LoggedIn = React.createClass({
  getInitialState: function() {
    return {
      profile: null
    }
  },

  componentDidMount: function() {
    // The token is passed down from the App component 
    // and used to retrieve the profile
    this.props.lock.getProfile(this.props.idToken, function (err, profile) {
      if (err) {
        console.log("Error loading the Profile", err);
        return;
      }
      this.setState({profile: profile});
    }.bind(this));
  },

  render: function() {
    if (this.state.profile) {
      return (
        <div>
        <img src={this.state.profile.picture} />
        <h2>Welcome {this.state.profile.nickname}</h2>
        </div>
      );
    } else {
      return (
        <div className="loading">Loading profile</div>
      );
    }
  }
});

var Lock = React.createClass({
  componentWillMount: function() {
    // Set the state with a property that has the token
    this.lock = new Auth0Lock('IGFzIUYJOkGqTKAChSynSXFjxVDJreTE', 'surajsongire1.auth0.com');
    this.setState({idToken: this.getIdToken()})

  },
  createLock: function() {
    this.lock = new Auth0Lock('IGFzIUYJOkGqTKAChSynSXFjxVDJreTE', 'surajsongire1.auth0.com');
  },
  getIdToken: function() {
    // First, check if there is already a JWT in local storage
    var idToken = localStorage.getItem('id_token');
    var authHash = this.lock.parseHash(window.location.hash);
    // If there is no JWT in local storage and there is one in the URL hash,
    // save it in local storage
    if (!idToken && authHash) {
      if (authHash.id_token) {
        idToken = authHash.id_token
        localStorage.setItem('id_token', authHash.id_token);
      }
      if (authHash.error) {
        // Handle any error conditions
        console.log("Error signing in", authHash);
      }
    }
    return idToken;
  },
  render: function() {
    if (this.state.idToken) {
      return (<LoggedIn lock={this.lock} idToken={this.state.idToken} />);
    } else {
      return (<Home lock={this.lock} />);
    }
  }
});

export default Lock;