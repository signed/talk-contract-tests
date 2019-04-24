module.exports = {
  "globals": {
    "ts-jest": {
      "diagnostics": {
        "warnOnly": true,
        "ignoreCodes": [
          "TS151001"
        ]
      }
    }
  },
  rootDir: '../',
  preset: 'ts-jest',
  testEnvironment: 'node',
  roots: ['<rootDir>/pacts'],
  moduleDirectories: ['<rootDir>/node_modules', '<rootDir>/src'],
  testMatch: ["**/pacts/**/*.pact.ts"]
};
