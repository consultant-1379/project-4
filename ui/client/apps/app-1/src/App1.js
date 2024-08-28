/**
 * App1 is defined as
 * `<e-app-1>`
 *
 * Imperatively create application
 * @example
 * let app = new App1();
 *
 * Declaratively create application
 * @example
 * <e-app-1></e-app-1>
 *
 * @extends {App}
 */
import { definition } from '@eui/component';
import { App, html } from '@eui/app';
import style from './app1.css';

@definition('e-app-1', {
  style,
  props: {
    response: { attribute: false },
  },
})
export default class App1 extends App {
  /**
   * Uncomment this block to add initialization code
   * constructor() {
   *   super();
   *   // initialize
   * }
   */

  /**
  * Render the <e-app-1> app. This function is called each time a
  * prop changes.
  */
  render() {
    const { EUI } = window;
    return html`
    <h1>${(EUI && EUI.Localizer.loc.APP_DEFAULT_TEXT) ||
      'Your app markup goes here'}</h1>
      <eui-table-v0>
      </eui-table-v0>
        <script>
        const table = document.querySelector("eui-table-v0");
        table.columns = [
          { title: "Title 1", attribute: "col1", sortable: true },
          { title: "Title 2", attribute: "col2", sortable: true },
          { title: "Title 3", attribute: "col3", sortable: true },
          { title: "Title 4", attribute: "col4", sortable: true },
        ];
        table.data = [
          { col1: "Item 1", col2: "Details 1", col3: "Details 1", col4: "Details 1" },
          { col1: "Item 2", col2: "Details 2", col3: "Details 2", col4: "Details 2" },
          { col1: "Item 3", col2: "Details 3", col3: "Details 3", col4: "Details 3" },
          { col1: "Item 4", col2: "Details 4", col3: "Details 4", col4: "Details 4" },
          { col1: "Item 5", col2: "Details 5", col3: "Details 5", col4: "Details 5" },
          { col1: "Item 6", col2: "Details 6", col3: "Details 6", col4: "Details 6" },
          ];
        </script>
      `;
  }
}

/**
 * Register the component as e-app-1.
 * Registration can be done at a later time and with a different name
 * Uncomment the below line to register the App if used outside the container
 */
// App1.register();
