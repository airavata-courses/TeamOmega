import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import './main.css';

class App extends Component {
  render() {
    return (
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
          <a href="#why" className="btn btn-lg btn-border wow fadeInUp" data-wow-duration="1000ms" data-wow-delay="400ms">Login</a>
        </div>   
       </div>
    </header>
    </div>
    );
  }
}

export default App;
