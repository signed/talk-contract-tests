import pact from '@pact-foundation/pact-node';
import type { PublisherOptions } from '@pact-foundation/pact-node';
import path from 'path';

const opts: PublisherOptions = {
  pactFilesOrDirs: [
    path.resolve(
      __dirname,
      '../../../pacts/ts-subtraction-calculatorservice.json'
    )
  ],
  pactBroker: 'http://pactbroker',
  //  pactBrokerUsername: "dXfltyFMgNOFZAxr8io9wJ37iUpY42M",
  //  pactBrokerPassword: "O5AIZWxelWbLvqMd8PkAVycBJh2Psyg1",
  //  tags: ["prod", "test"],
  consumerVersion:
    '0.0.2'
};

pact
  .publishPacts(opts)
  .then(() => {
    console.log('Pact contract publishing complete!');
  })
  .catch((e: Error) => {
    console.log('Pact contract publishing failed: ', e);
  });
