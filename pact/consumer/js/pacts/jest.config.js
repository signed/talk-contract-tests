module.exports = {
  rootDir: '../',
  testEnvironment: 'node',
  roots: ['<rootDir>/pacts'],
  moduleDirectories: ['<rootDir>/node_modules', '<rootDir>/src'],
  testMatch: ["**/pacts/**/*.pact.js"]
};


