// import express and create a new router
const router = require('express').Router();


/**
 * MusicVideoManagerWebHandler module
 * (contains MusicVideoManagerWebHandler object)
 */
const musicVideoManagerWebHandler = require('../musicVideoManagerWebHandler');


/**
 * Edit a song/music video
 */
router.post('/song/', (req, res) => {

  // debug info
  console.log(">> Post \"edit song\"");
  console.log(req.baseUrl + req.url);
  console.log(req.body);

  res.send("TODO");

});


/**
 * Edit a playlist entry
 */
router.post('/playlist/', (req, res) => {

  // debug info
  console.log(">> Post \"edit a playlist entry\"");
  console.log(req.baseUrl + req.url);
  console.log(req.body);

  res.send("TODO");

});


/**
 * Edit a comment
 */
router.post('/comment/', (req, res) => {

  // debug info
  console.log(">> Post \"edit a comment\"");
  console.log(req.baseUrl + req.url);
  console.log(req.body);

  res.send("TODO");

});


/**
 * Edit a user
 */
router.post('/user/', (req, res) => {

  // debug info
  console.log(">> Post \"edit a user\"");
  console.log(req.baseUrl + req.url);
  console.log(req.body);

  res.send("TODO");

});


/**
 * Redirect the user to the error site
 */
router.get((req, res) => {
  let err = new Error('Not Found');
  err.status = 404;
  next(err);
});


// export router back to express
module.exports = router;
