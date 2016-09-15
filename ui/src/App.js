import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import './main.css';
import Auth0Lock from 'auth0-lock';


var Lock = React.createClass({
  // ...
  showLock: function() {
    // Show the Auth0Lock widget
    this.lock = new Auth0Lock('IGFzIUYJOkGqTKAChSynSXFjxVDJreTE', 'surajsongire1.auth0.com');
    this.lock.show();
  },

  render: function() {
    return (
    <div className="login-box">
      <a onClick={this.showLock} className="btn btn-lg btn-border wow fadeInUp" data-wow-duration="1000ms" data-wow-delay="400ms">Login</a>
    </div>);
  }
});

var LandingPage = React.createClass({
  render: function(){
    return(
    <div>
       <header className="hero-area" id="home">
      <div className="container">
          <div className="col-md-12">

            <div className="navbar navbar-inverse sticky-navigation navbar-fixed-top" role="navigation" data-spy="affix" data-offset-top="200">
              <div className="container">
                <div className="navbar-header">
                  <a className="logo-left " href="index.html"><i className="mdi-image-timelapse"></i>Omega</a>
                </div>
           
              </div>
            </div>
        </div>        
        <div className="contents text-right">
          <h1 className="wow fadeInRight" data-wow-duration="1000ms" data-wow-delay="300ms">Welcome to Weather Prediction Service</h1>
          <p className="wow fadeInRight" data-wow-duration="1000ms" data-wow-delay="400ms">By Team Omega</p>
         <Lock />
        </div>   
       </div>
    </header>
    </div>
    );
  }
});

class App extends Component {
  render() {
    return (
      <LandingPage />
    );
  }
}

export default App;
