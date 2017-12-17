// import express and create a new router
const router = require('express').Router();


/**
 * MusicVideoManagerWebHandler module
 * (contains MusicVideoManagerWebHandler object)
 */
const musicVideoManagerWebHandler = require('../musicVideoManagerWebHandler');


/**
 * Add a song/music video
 */
router.post('/song/', (req, res) => {

  // debug info
  console.log(">> Post \"add song\"");
  console.log(req.baseUrl + req.url);
  console.log(req.body);

  res.send("TODO");

});


/**
 * Add a playlist entry
 */
router.post('/playlist/', (req, res) => {

  // debug info
  console.log(">> Post \"add a playlist entry\"");
  console.log(req.baseUrl + req.url);
  console.log(req.body);

  res.send("TODO");

});


/**
 * Add a comment
 */
router.post('/comment/', (req, res) => {

  // debug info
  console.log(">> Post \"add a comment\"");
  console.log(req.baseUrl + req.url);
  console.log(req.body);

  res.send("TODO");

});


/**
 * Add a user
 */
router.post('/user/', (req, res) => {

  // debug info
  console.log(">> Post \"add a user\"");
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
