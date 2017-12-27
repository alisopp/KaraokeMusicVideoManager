var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');

const opn = require('opn');


/*
 * Custom routes get files:
 */
var playlist = require('./routes/playlist');
var songs = require('./routes/songs');
var add = require('./routes/add');
var remove = require('./routes/remove');
var edit = require('./routes/edit');


var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'hbs');

app.use(favicon(path.join(__dirname, 'public/favicons/', 'favicon.ico')));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));


/*
 * Custom routes use routes:
 */
app.use('/', playlist);
app.use('/songs', songs);
app.use('/add', add);
app.use('/remove', remove);
app.use('/edit', edit);


// catch 404 and forward to error handler
app.use(function (req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});

// error handler
app.use(function (err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};
  // render the error page
  res.status(err.status || 500);
  res.render('error', { title: 'my other page', layout: 'customLayout', color: '#000' });
});


/*var http = require('http');
http.createServer(function (req, res) {
  res.writeHead(200, {'Content-Type': 'text/plain'});
  res.end('Hello World\n');
}).listen(80, "127.0.0.1");
console.log('Server running at http://127.0.0.1:80/');*/



// react to custom user parameters besides "npm start"
process.argv.forEach((clCustomCommand) => {

  if (clCustomCommand === 'open') {
    // open the homepage at start with the default browser
    opn('http://127.0.0.1:3000/');
  }

});


module.exports = app;
