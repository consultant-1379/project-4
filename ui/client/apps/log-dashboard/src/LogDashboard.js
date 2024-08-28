/**
 * LogDashboard is defined as
 * `<e-log-dashboard>`
 *
 * Imperatively create application
 * @example
 * let app = new LogDashboard();
 *
 * Declaratively create application
 * @example
 * <e-log-dashboard></e-log-dashboard>
 *
 * @extends {App}
 */
import { definition } from '@eui/component';
import { App, html } from '@eui/app';
import { Notification } from '@eui/base';
import { LineChart } from '@eds/vanilla/charts/line-chart/LineChart';
import style from './logDashboard.css';
import '@eui/table';
import '@eui/layout'


@definition('e-log-dashboard', {
  style,
  props: {
    response: { },
    data: { type: Array },
    allLogColumns: { type: Array},
    allLogTableData: { type: Array},
    filteredLogTableData: {  type: Array},
    versionColumns: {type: Array},
    versionTableData: {  type: Array},
    _selected: { type: Array, default: [] },
    filterSev: { type: String, default: "all"},
    errorCount: {type: Number}
  },
})
export default class LogDashboard extends App {
      constructor() {
      super();
  
      this.allLogColumns = [
      {
        title: 'Severity', attribute: 'severity', sortable: true, resizable: true,
      },
      {
        title: 'Version', attribute: 'version', sortable: true, resizable: true,
      },
      {
        title: 'Date', attribute: 'date', sortable: true, resizable: true,
      },
      ,
      {
        title: 'Time', attribute: 'time', sortable: true, resizable: true,
      },
      {
        title: 'Message', attribute: 'message', sortable: true, resizable: true,
      },
    ];
    this.versionColumns = [
      {
        title: 'Version', attribute: 'version_name', sortable: true, resizable: true,
      },
      {
        title: 'Error', attribute: 'error', sortable: true, resizable: true,
      },
      {
        title: 'Warning', attribute: 'warning', sortable: true, resizable: true,
      },
      ,
      {
        title: 'Info', attribute: 'info', sortable: true, resizable: true,
      },
    ];
  }

  /**
   * Handle all events.
   * @param {Event} event - event dispatched from table and dropdown.
   */
  handleEvent(event) {

    if (event.type === 'click' && event.target.id === 'error-button') {
      this.filterSev = "ERROR"
      this.filterEvents()
    }
    if (event.type === 'click' && event.target.id === 'warning-button') {
      this.filterSev = "WARNING"
      this.filterEvents()

    }
    if (event.type === 'click' && event.target.id === 'info-button') {
      this.filterSev = "INFO"
      this.filterEvents()

    }
    if (event.type === 'click' && event.target.id === 'all-button') {
      this.filterSev = "all"
      this.filterEvents()

    }
    if (event.type === 'change') {
      const table = this.shadowRoot.querySelector('eui-table-v0');
      table[event.detail.name] = event.detail.checked;

      // reset the data when switching off both multi and single select.
      if (!table.singleSelect && !table.multiSelect) {
        this.data.forEach((row) => {
          row.selected = false;
        });
        this._selected = [];
        this.data = [...this.data];
      }

      if (table.fixed) {
        table.fixedHeight = 150;
      }
    }
    if (event.type === 'row-selected') {
      this._selected = this.data.filter(item => item.selected === true);
      this.data = [...this.data];
    }
    if (event.type === 'eui-table:sort') {
      this.filteredLogTableData.sort((cellA, cellB) => {
        const a = cellA[event.detail.column.attribute].toUpperCase();
        console.log(a)
        const b = cellB[event.detail.column.attribute].toUpperCase();
        console.log(a)
        if (a < b) {
          return event.detail.sort === 'asc' ? -1 : 1;
        } if (a > b) {
          return event.detail.sort === 'asc' ? 1 : -1;
        }
        return 0;
      });
      this.filteredLogTableData = [...this.filteredLogTableData];
    }
  }

  getMetaData(endpoint, dataName) {
      fetch(`${endpoint}`)
        .then((response) => {
          return response.json();
        })
        .then((json) => {
          this[dataName] = json; 
        });
    }
  
    filterEvents() {
      this.errorCount = 0;
      let temp_events = [];
      (this.allLogTableData).forEach(element => {
        if(element.severity == "ERROR"){
          this.errorCount += 1;
        }
        if (
          this.filterSeverity(element.severity, this.filterSev)
         ) {
          temp_events = [...temp_events, element]
        }
      })
      this.filteredLogTableData = temp_events
      if(this.errorCount > 100){
        this.triggerNotification();
      }
    }

    filterSeverity(event, filterSev) {
      if (event === filterSev || filterSev === 'all') {
        return true;
      }
      return false;
    }
  
    didConnect(){
      //this.getMetaData('http://localhost:9000/allLogs', 'allLogTableData')
      //this.getMetaData('http://localhost:9000/versionStats', 'versionTableData')
      this.getMetaData('./assets/log.json', 'allLogTableData')
      this.getMetaData('./assets/version.json', 'versionTableData')

      this.filterEvents()

    }

    didChangeProps(changedProps){
    }

    didUpgrade() {
      this._loadLineChart(); // loads chart out of box in app-1
    }

    triggerNotification() {
      const notification = document.createElement('eui-base-v0-notification');
      notification.description = 'Error Count Threshold';
      notification.textContent = 'The number of errors encountered has surpassed the set limit!';
      notification.timeout = 60000;
    
      const notificationDescription = document.createElement('div');
      notificationDescription.setAttribute('slot', 'description');
      notificationDescription.innerHTML = 'Current Error Count: ' + this.errorCount;
    
      notification.appendChild(notificationDescription);
    
      notification.addEventListener('click', event => {
        console.log('clicked');
      });
      notification.showNotification();
    }

    _loadLineChart() {
      const lineData = {
        series: [{
          name: 'Errors per day',
          values: [273, 503, 671, 100, 512, 34, 26, 268, 427, 351, 589, 252, 8, 17],
        }],
        common: ['2019-03-07', '2019-03-08', '2019-03-09', '2019-03-10', '2019-03-11', '2019-03-12', '2019-03-13', '2019-03-14', '2019-03-15', '2019-03-16', '2019-03-17'],
      };
      const element = this.shadowRoot.querySelector('#line');
      const linechart = new LineChart({
        element,
        data: lineData,
        height: 250,
        margin: {
          left: 45,
          bottom: 65,
        },
        y: {
          unit: 'Errors',
        },
      });
      linechart.init();
    }

  render() {
    const { EUI } = window;
    return html`

    <div class="layout__dashboard">
    
      <eui-layout-v0-tile tile-title="Python Log Simulator Analysis" column=0 column-span=3 >
        <div class="table-demo__content" slot="content">
          <p>
          A useful dashboard for checking on the health of an application.
          </p>
          Click to filter logs based on severity
          <br>
          <br>
          <eui-base-v0-button id="error-button" @click=${this}>Errors</eui-base-v0-button>
          <eui-base-v0-button id="warning-button" @click=${this}>Warning</eui-base-v0-button>
          <eui-base-v0-button id="info-button" @click=${this}>Info</eui-base-v0-button>
          <eui-base-v0-button id="all-button" @click=${this}>All</eui-base-v0-button>
        </div>
      </eui-layout-v0-tile>
      
      <eui-layout-v0-tile tile-title="Version Graph"  column-span=1>
      <div id="line" slot="content"></div>
    </eui-layout-v0-tile>


      <eui-layout-v0-tile tile-title="Version Statistics" subtitle="${this.versionTableData.length} items | ${this._selected.length} selected" column=0 column-span=2>
      <div class="table-demo__content" slot="content">
        <eui-table-v0 .columns=${this.versionColumns} .data=${this.versionTableData} .numEntries=${10}></eui-table-v0>
      </div>
      </eui-layout-v0-tile>

    



      <eui-layout-v0-tile tile-title="All Logs Captured" subtitle="${this.filteredLogTableData.length} items " column=0 column-span=3 maximizable>
        <div class="table-demo__content" slot="content" >
          <eui-table-v0 .columns=${this.allLogColumns} .data=${this.filteredLogTableData} @eui-table:sort=${this} sortable></eui-table-v0>
        </div>

        <div slot="footer">

        </div>
      </eui-layout-v0-tile>

     



    </div>
  `;
  }
}

/**
 * Register the component as e-log-dashboard.
 * Registration can be done at a later time and with a different name
 * Uncomment the below line to register the App if used outside the container
 */
// LogDashboard.register();
