const path = require('path');
const chai = require('chai');
const {Pact} = require('@pact-foundation/pact');
const chaiAsPromised = require('chai-as-promised');
const SubtractionClient = require('../src/SubtractionClient');

const expect = chai.expect;
const MOCK_SERVER_PORT = 2202;
const host = `http://localhost:${MOCK_SERVER_PORT}`;
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
        state: 'calculator online',
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
      return new SubtractionClient(host).subtract(43.0, 42.0)
        .then((response) => {
          expect(response.result).to.be.a('number');
          expect(response.result).to.equal(1);
        })
    });

    it("successfully verifies", () => provider.verify());

  })
});
