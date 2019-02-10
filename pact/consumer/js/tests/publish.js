const pact = require("@pact-foundation/pact-node");
const path = require("path");
const opts = {
  pactFilesOrDirs: [
    path.resolve(
      __dirname,
      "../../../pacts/"
    ),
  ],
  pactBroker: "http://pactbroker",
//  pactBrokerUsername: "dXfltyFMgNOFZAxr8io9wJ37iUpY42M",
//  pactBrokerPassword: "O5AIZWxelWbLvqMd8PkAVycBJh2Psyg1",
//  tags: ["prod", "test"],
  consumerVersion:
    "0.0.2"
};

pact
  .publishPacts(opts)
  .then(() => {
    console.log("Pact contract publishing complete!");
  })
  .catch(e => {
    console.log("Pact contract publishing failed: ", e)
  });
