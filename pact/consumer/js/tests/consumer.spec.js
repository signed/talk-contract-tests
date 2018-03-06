const path = require('path')
const chai = require('chai')
const { Pact } = require('@pact-foundation/pact')
const fetch = require('isomorphic-unfetch')
const chaiAsPromised = require('chai-as-promised')

const expect = chai.expect
const MOCK_SERVER_PORT = 2202
const LOG_DIR = path.resolve(process.cwd(), 'logs', 'pact.log')
const PACT_DIR = path.resolve(process.cwd(), 'pacts')

chai.use(chaiAsPromised);

describe('Calculator Pact', () => {

	// (1) Create the Pact object to represent your provider
	const provider = new Pact({
		consumer: 'SubstractionService',
		provider: 'CalculatorServiceJS',
		port: MOCK_SERVER_PORT,
		log: LOG_DIR,
		dir: PACT_DIR,
		logLevel: 'ERROR', // specifies the cli log-level (use debug or info)
		spec: 2
	})

	// this is the response you expect from your Provider
	const EXPECTED_BODY = {
		"result": 1
	}

	context('when calculator is on', () => {
		before((done) => {
			// (2) Start the mock server
			provider.setup()
				// (3) add interactions to the Mock Server, as many as required
				.then(() => {
					return provider.addInteraction({
						// The 'state' field specifies a "Provider State"
						state: 'when the calculator is on',
						uponReceiving: 'a request to substract two numbers',
						withRequest: {
							method: 'POST',
							path: '/basic/substraction',
							body: JSON.stringify({ subtrahends: [43, 42] }),
							headers: { 'Content-Type': 'application/json; charset=utf-8' }
						},
						willRespondWith: {
							status: 200,
							headers: { 'Content-Type': 'application/json; charset=utf-8' },
							body: EXPECTED_BODY
						}
					})
				})
				.then(() => done())
		})

		// (4) write your test(s)
		it('should subtract two numbers', () => {
			return fetch(`http://localhost:${MOCK_SERVER_PORT}/basic/substraction`, {
				method: 'POST',
				headers: { 'Content-Type': 'application/json; charset=utf-8' },
				body: JSON.stringify({ subtrahends: [43, 42] })
			}) // TODO: Update with actual method from actual client lib
				.then(r => r.json())
				.then((result) => {
					console.log(result);
					expect(result).to.be.a('number')
					expect(result).to.equal(1)
					// (5) validate the interactions you've registered and expected occurred
					// this will throw an error if it fails telling you what went wrong
					return provider.verify();
				})
		})

		// (6) write the pact file for this consumer-provider pair,
		// and shutdown the associated mock server.
		// You should do this only _once_ per Provider you are testing.
		after(() => {
			provider.finalize()
		})
	})
})
