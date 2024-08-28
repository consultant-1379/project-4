/**
 * Integration tests for <e-log-dashboard>
 */
import { expect } from 'chai';
import LogDashboard from '../LogDashboard';
import {
  inShadow,
  injectHTMLElement,
} from '../../../../../test/utils';

describe('LogDashboard Application Tests', () => {
    let container;
    let inject;
    before(() => {
      container = document.body.appendChild(document.createElement('div'));
      inject = injectHTMLElement.bind(null, container);
      window.EUI = undefined; // stub out the locale
      LogDashboard.register();
    });

    after(() => {
      document.body.removeChild(container);
    });

    describe('Basic application setup', () => {
      it('should create a new <e-log-dashboard>', async () => {
        const appUnderTest = await inject('<e-log-dashboard></e-log-dashboard>');
        // check shadow DOM
        const headingTag = inShadow(appUnderTest, 'h1');
        expect(headingTag.textContent, '"Your app markup goes here" was not found').to.equal('Your app markup goes here');
      });
    });
});
