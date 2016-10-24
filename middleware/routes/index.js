var express = require('express');
var passport = require('passport');
var router = express.Router();




var ensureLoggedIn = require('connect-ensure-login').ensureLoggedIn();

//setting up dot env file and loading it
var dotenv = require('dotenv');
dotenv.load();

//Passport credentials
var env = {
  AUTH0_CLIENT_ID: process.env.AUTH0_CLIENT_ID,
  AUTH0_DOMAIN: process.env.AUTH0_DOMAIN,
  AUTH0_CALLBACK_URL: process.env.AUTH0_CALLBACK_URL || 'http://localhost:3000/callback'
};


var login_page_link = ['/','/login']
/* GET login page. */
router.get(login_page_link, function(req, res, next) {
    var cookie = req.cookies.email;
    if (!req.session.isAuthenticated)
    {
      // no: set a new cookie
      /*var randomNumber=Math.random().toString();
      randomNumber=randomNumber.substring(2,randomNumber.length);
      res.cookie('cookieName',randomNumber, { maxAge: 900000, httpOnly: true });
      console.log('cookie created successfully');
      */
      console.log("Coming to initial login");
  
      var toSend = {
        title: 'Express',
        env: env
      }
      res.render('login', {tosend : toSend});  
    } 
    else
    {
      // yes, cookie was already present
      res.redirect('/home');
    } 
    //next(); // <-- important!
 
});


//Log Out page
router.get('/logout', function(req, res){
  res.clearCookie('email');
  req.session.destroy();
  req.logout();
  res.redirect('/');
});


//Callback link
router.get('/callback',
  passport.authenticate('auth0', { failureRedirect: '/url-if-something-fails' }),
  function(req, res) {
    request.session.cookie.maxAge = 3600000
    request.cookies.maxAge = 3600000
    req.session.isAuthenticated = true;
    var email = req.session.passport.user.emails[0].value;
    // console.log(req.session.passport.user);
    res.cookie('email',email, { maxAge: 900000, httpOnly: false });
    res.redirect(req.session.returnTo || '/home');

  });


module.exports = router;
