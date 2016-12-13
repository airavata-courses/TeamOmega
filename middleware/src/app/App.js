import React from 'react';
import 'react-toolbox/lib/commons.scss';           // Import common styles
import PurpleAppBar from './PurpleAppBar.js';      // AppBar with simple overrides
import { Button } from 'react-toolbox/lib/button'; // Bundled component import
import { Card, CardMedia, CardTitle, CardText, CardActions } from 'react-toolbox/lib/card';
import DatePickerIn from './DatePicker.js';



import {Tab, Tabs} from 'react-toolbox';


import Table from 'react-toolbox/lib/table';

const UserModel = {
  location: {type: String},
  birthdate: {type: Date,
    title: 'Date'},
		status: {type: String}
};

const users = [
  {location: 'New York',  birthdate: new Date(1980, 3, 11), status: 'Completed'},
  {location: 'Javi Velasco', birthdate: new Date(1987, 1, 1), status:'Running'}
];

class TableTest extends React.Component {
  state = { selected: [], source: users };

  handleChange = (row, key, value) => {
    const source = this.state.source;
    source[row][key] = value;
    this.setState({source});
  };

  handleSelect = (selected) => {
    this.setState({selected});
  };

  render () {
    return (
      <Table
        model={UserModel}
        onChange={this.handleChange}
        onSelect={this.handleSelect}
        selectable
        multiSelectable
        selected={this.state.selected}
        source={this.state.source}
      />
    );
  }
}
class TabsTest extends React.Component {
  state = {
    fixedIndex: 1,
  };

  handleTabChange = (index) => {
    this.setState({index});
  };

  handleFixedTabChange = (index) => {
    this.setState({fixedIndex: index});
  };


  handleActive = () => {
    console.log('Special one activated');
  };

  render () {
    return (
      <section>

        <Tabs index={this.state.fixedIndex} onChange={this.handleFixedTabChange} fixed>
          <Tab label='Weather Forecast'><DatePickerIn /></Tab>
          <Tab label='History' onActive={this.handleActive}><TableTest /></Tab>
        </Tabs>

      </section>
    );
  }
}





const SearchCards = () => (
	<div className="mdl-grid">
   		<div className="mdl-layout-spacer"></div>
   			<div className="mdl-cell mdl-cell--4-col">
 			  <Card raised={true} style={{width: "auto"}}>
        <CardTitle title="Search service"  subtitle="Select the date and location"/>
    <CardText><TabsTest /></CardText>


  </Card>
  </div>
   		<div className="mdl-layout-spacer"></div>
  </div>
);

const App = () => (
  <div className="landing">
    <PurpleAppBar />
    <section style={{ padding: 20 }}>
    <SearchCards />


    </section>
  </div>
);

export default App;
