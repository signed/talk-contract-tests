const fetch = require('isomorphic-unfetch');

class SubtractionResult {

  constructor(result) {
    this.result = result;
  }

}

class SubtractionClient {
  constructor(host) {
    this.host = host;
  }

  subtract(minuend, subtrahend) {
    return fetch(this.host + `/basic/subtraction`, {
      method: 'POST',
      headers: {'Content-Type': 'application/json;charset=utf-8'},
      body: JSON.stringify({minuend: minuend, subtrahends: [subtrahend]})
    }).then(response => response.json())
      .then(json => new SubtractionResult(json.result));
  }
}

module.exports = SubtractionClient;