// import sqlite 3
const sqlite3 = require('sqlite3').verbose();

/**
 * Name/Path of the database
 */
const databasePath = './music_video_manager_database.db';

/**
 * Open a database connection - no callback needed(?)
 * @param {string} info optional (debug) name of connection
 * @returns {sqlite3.Database} sqlite database
*/
function open_sqlite_db(info = "no name") {
  return new sqlite3.Database(databasePath, sqlite3.OPEN_READWRITE, (err) => {
    if (err) {
      console.error(err.message);
    } else {
      console.log('Connected to the database (' + info + ')');
    }
  });
}

/**
 * Make a get request to the database and get the list as the first parameter in the callback function
 * @param {string} query query for the database
 * @param {string} name optional (debug) name of query
 * @param {function} callback optional callback function -> one parameter which returns the requested list
 */
function make_sql_request_get(query, name = "no name", callback = console.log) {

  // create empty list for objects
  let requestedList = [];

  // open/connect to main database
  let db = open_sqlite_db(name);

  // make the request...
  console.log("SQLITE GET " + name + ":");
  // ...if the main database exists
  if (db !== undefined || db !== null) {
    db.serialize(() => {
      db.each(query, (err, row) => {
        if (err) console.error(err.message)

        // add the matching row from the database to the (empty) list
        requestedList.push(row);

      });
    }).close((err) => {
      if (err) console.error(err.message)

      // close the database connection
      console.log('Closed the database connection (' + name + ')');

      // then call the callback function with the given function and the requested list as parameter
      callback(requestedList);

    });
  } else console.error("database not opened")

}


/**
 * Edit the database and get true/false as parameter in the callback function
 * @param {string} query query for the database
 * @param {[]} properties the data for the query
 * @param {string} name optional (debug) name of query
 * @param {function} callback optional callback function -> one parameter which returns if everything worked (true) or not (false)
 */
function make_sql_request_edit(query, properties, name = "no name", callback = console.log) {

  // open/connect to main database
  let db = open_sqlite_db(name);

  // make the request...
  console.log("SQLITE EDIT/DO " + name + ":");
  // ...if the main database exists
  if (db !== undefined || db !== null) {
    db.serialize(function () {
      db.run(query, properties, function (err) {

        // if there is an error return false in the callback function
        if (err) {
          console.error(err.message);
          callback(false);
        } else {
          // else log the change/action in the console
          console.log(name + ` in rows ${this.changes}`);
        }

      });
    }).close((err) => {

      // close the database
      if (err) {
        // if there is an error return false in the callback function
        console.error(err.message);
        callback(false);
      } else {
        // else return that everything worked out in the callback function
        console.log('Closed the database connection (' + name + ')');
        callback(true);
      }

    });
  } else {
    // if there is an error return false in the callback function
    console.error("database not opened");
    callback(false);
  }

}


/**
 * Class that has access and functions to manipulate the database
 * @author AnonymerNiklasistanonym >> https://github.com/AnonymerNiklasistanonym
 */
class MusicVideoManagerDatabaseHandler {

  // Constructor
  constructor() { console.log(">> Created MusicVideoManagerDatabaseHandler object"); }

  // New Methods

  getPlaylist() {
    
  }

  // Getter (without callback)

  get memberList() {
    return make_sql_request_get(`SELECT * FROM member`, "member list");
  }
  get bookList() {
    return make_sql_request_get(`SELECT * FROM book`, "book list");
  }
  get bookLoanList() {
    return make_sql_request_get(`SELECT * FROM book_loan`, "book loan list");
  }

  // "Getter" (with callback)

  /**
   * Get member list from database
   * @param {function} callback callback function -> one parameter which returns the list
   */
  getMemberList(callback) {
    make_sql_request_get(`SELECT * FROM member ORDER BY LOWER(first_name), LOWER(last_name), address`, "member list", callback);
  }

  /**
   * Get book list from database
   * @param {function} callback callback function -> one parameter which returns the list
   */
  getBookList(callback) {
    make_sql_request_get(`SELECT * FROM book ORDER BY LOWER(title), LOWER(author), isbn`, "book list", callback);
  }

  /**
  * Get book loan list from database
  * @param {function} callback callback function -> one parameter which returns the list
  */
  getBookLoanList(callback) {
    make_sql_request_get(`SELECT * FROM book_loan ORDER BY date`, "book loan list", callback);
  }

  /**
   * Get a book list which only contains books that match in some way the request query
   * @param {string} requestQuery query after which the database should be searched
   * @param {string} nameOfAction info about what is processed right now (for debugging)
   * @param {function} callback callback function -> one parameter which returns the list
   */
  getbookListRequest(requestQuery, nameOfAction, callback) {
    make_sql_request_get("SELECT * FROM book WHERE (title LIKE '%" + requestQuery + "%' OR author LIKE '%" + requestQuery + "%' OR isbn LIKE '%" + requestQuery + "%') ORDER BY LOWER(title), LOWER(author), isbn ASC",
      nameOfAction, (requestedBookList) => {
        callback(requestedBookList);
      }
    );
  }

  // Edit the database functions (with callback)

  /**
   * Add a book to the database
   * @param {{title:string,author:string,isbn:number}} book the book that should be added
   * @param {function} callback function -> one parameter which is true if the book was added or false if not
   */
  addBook(book, callback) {
    const book_array = [book.title, book.author, book.isbn];
    make_sql_request_edit("INSERT INTO book VALUES (null,?,?,?)", book_array, "insert book", callback);
  }

  /**
   * Remove a book from the database
   * @param {number} book_id the id of the book in the database
   * @param {function} callback callback function -> one parameter which is true if the book was removed or false if not
   */
  removeBook(book_id, callback) {
    make_sql_request_edit(`DELETE FROM book WHERE id=?`, book_id, 'book', callback);
  }

  /**
   * Add a book loan to the database
   * @param {{book_id:number,member_id:number,date:number}} bookLoan the book loan that should be added
   * @param {function} callback callback function -> one parameter which is true if the book loan was added or false if not
   */
  addBookLoan(bookLoan, callback) {
    const book_loan_array = [bookLoan.book_id, bookLoan.member_id, bookLoan.date];
    make_sql_request_edit(`INSERT INTO book_loan VALUES(null, ?, ?, ?)`, book_loan_array, 'book_loan', callback);
  }

  /**
   * Remove a book loan from the database
   * @param {number} bookLoan_id the id of the book loan in the database
   * @param {function} callback callback function -> one parameter which is true if the book loan was removed or false if not
   */
  removeBookLoan(bookLoan_id, callback) {
    make_sql_request_edit(`DELETE FROM book_loan WHERE id=?`, bookLoan_id, "remove book loan", callback);
  }

  /**
   * Add a member to the database
   * @param {{first_name:string,last_name:string,address:string}} member the member that should be added
   * @param {function} callback callback function -> one parameter which is true if the member was added or false if not
   */
  addMember(member, callback) {
    const member_array = [member.first_name, member.last_name, member.address];
    make_sql_request_edit(`INSERT INTO member VALUES (null,?,?,?)`, member_array, 'insert member', callback);
  }

  /**
   * Remove a member from the database
   * @param {number} member_id the id of the member in the database
   * @param {function} callback callback function -> one parameter which is true if the member was removed or false if not
   */
  removeMember(member_id, callback) {
    make_sql_request_edit(`DELETE FROM member WHERE id=?`, member_id, 'delete member', callback);
  }

}


// create one instance of DatabaseHandler for the whole project
// export the object to every script that requires this module
// through this only one object will ever be created
module.exports = new MusicVideoManagerDatabaseHandler();
