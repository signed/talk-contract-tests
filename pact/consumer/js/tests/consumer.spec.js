const path = require('path');
const chai = require('chai');
const {Pact} = require('@pact-foundation/pact');
const fetch = require('isomorphic-unfetch');
const chaiAsPromised = require('chai-as-promised');

const expect = chai.expect;
const MOCK_SERVER_PORT = 2202;
const LOG_DIR = path.resolve(process.cwd(), 'pact/consumer/js/logs', 'pact.log');
const PACT_DIR = path.resolve(process.cwd(), 'pact/pacts');

chai.use(chaiAsPromised);

describe('Calculator Pact', () => {

  const provider = new Pact({
    consumer: 'SubtractionService',
    provider: 'CalculatorService',
    port: MOCK_SERVER_PORT,
    log: LOG_DIR,
    dir: PACT_DIR,
    logLevel: 'FATAL',
    spec: 2
  });

  before(() => provider.setup());

  after(() => provider.finalize());

  context('when calculator is on', () => {
    before(() => provider.addInteraction({
        // The 'state' field specifies a "Provider State"
        state: 'when the calculator is on',
        uponReceiving: 'a request to subtract two numbers',
        withRequest: {
          method: 'POST',
          path: '/basic/subtraction',
          body: {minuend: 43.0, subtrahends: [42.0]},
          headers: {'Content-Type': 'application/json;charset=utf-8'}
        },
        willRespondWith: {
          status: 200,
          headers: {'Content-Type': 'application/json;charset=utf-8'},
          body: {"result": 1}
        }
      })
    );

    it('should subtract two numbers', () => {
      return fetch(`http://localhost:${MOCK_SERVER_PORT}/basic/subtraction`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json;charset=utf-8'},
        body: JSON.stringify({minuend: 43.0, subtrahends: [42.0]})
      }).then(r => r.json())
        .then((response) => {
          expect(response.result).to.be.a('number');
          expect(response.result).to.equal(1);
        })
    });

    it("successfully verifies", () => provider.verify());

  })
});
