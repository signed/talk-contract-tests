import  { Pact } from '@pact-foundation/pact';
import { SubtractionClient } from '../src/SubtractionClient';
import * as path from 'path';

const port = 2202;
const host = `http://localhost:${port}`;

describe('Calculator Pact', () => {

  const provider = new Pact({
    port: port,
    logLevel: "error",
    log: path.resolve(process.cwd(), "build/logs", "mockserver-integration.log"),
    dir: path.resolve(process.cwd(), "../../pacts"),
    spec: 2,
    pactfileWriteMode: "update",
    consumer: "TS-Subtraction",
    provider: "CalculatorService",
  });

  beforeAll(() => provider.setup());

  afterAll(() => provider.finalize());

  afterEach(() => provider.verify());

  describe('when calculator is on', () => {
    beforeEach(() => {
      return provider.addInteraction({
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
    });

    it('should subtract two numbers', () => {
      return new SubtractionClient(host).subtract(43.0, 42.0)
        .then((response) => {
          expect(typeof response.result).toBe('number');
          expect(response.result).toEqual(1);
        })
    });
  });
})
