import React from 'react';
import 'react-toolbox/lib/commons.scss';           // Import common styles
import PurpleAppBar from './PurpleAppBar.js';      // AppBar with simple overrides
import { Button } from 'react-toolbox/lib/button'; // Bundled component import
import { Card, CardMedia, CardTitle, CardText, CardActions } from 'react-toolbox/lib/card';
import DatePickerIn from './DatePicker.js';
import SubmitButton from './SubmitButton.js'



import {Tab, Tabs} from 'react-toolbox';


import Table from 'react-toolbox/lib/table';

const UserModel = {
  req_no: {type: String},
  location: {type: String},
  birthdate: {type: Date,
    title: 'Date'},
    hour: {type: Number},
    min: {type: Number},
    sec: {type: Number},
    process: {type: String},
		status: {type: String},
    last_updated: {type: String}
};
// {location: 'New York',  birthdate: new Date(1980, 3, 11), status: 'Completed'},
// {location: 'Javi Velasco', birthdate: new Date(1987, 1, 1), status:'Running'}
var email = getCookie("email");

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i = 0; i <ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return  decodeURIComponent(c.substring(name.length,c.length));
        }
    }
    return "";
}

class TableTest extends React.Component {
  state = { selected: [], submitDisabled: "" };

  handleChange = (row, key, value) => {
    const source = this.state.source;
    source[row][key] = value;
    this.setState({source});
  };

  handleSelect = (selected) => {
    this.setState({selected});
    console.log()
  };
  handleSubmit = () => {
 for (var i = 0; i < this.state.selected.length; i++){
    var d = this.props.source[i].birthdate;
    var month = d.getMonth();
    var day = d.getDate();
    if(day<10){
      day = '0'+day.toString()
    }
    if(month<10){
      month = '0'+month.toString()
    }
    var date_format = d.getFullYear()+"/"+month+"/"+day+"/"+this.props.source[i].code+"/";
    var timestamp = [this.props.source[i].hour, this.props.source[i].min, this.props.source[i].sec];
    var time1 = '';
     for (var item in timestamp){
       if (item <0){
         item *= -1
       }
       if (item < 10){
         time1+=("0"+String(item));
       }
       else{
         time1+=(String(item));
       }
     }

    fetch('/home/submit',{method: "POST", credentials: 'same-origin', headers: {
 'Accept': 'application/json',
 'Content-Type': 'application/json'
},
body: JSON.stringify({room:email+"-resubmit", date:date_format , type:1,timest:time1, req_no : this.props.source[i].req_no})

})
 .then(function(res) {
     return res.text();
 }).then(function(body) {

 });



}


  }

  render () {
    return (
      <div>
      <Table
        model={UserModel}
        onChange={this.handleChange}
        onSelect={this.handleSelect}
        selectable
        multiSelectable
        selected={this.state.selected}
        source={this.props.source}
      />
      <SubmitButton
        accent
        raised
        primary
        disabled = {!this.state.selected.length}
        label = "Resubmit"
        onClick={this.handleSubmit}
      />
      </div>
    );
  }
}
class TabsTest extends React.Component {
  state = {
    fixedIndex: 0, source: [] };

  handleTabChange = (index) => {
    this.setState({index});
  };

  handleFixedTabChange = (index) => {
    this.setState({fixedIndex: index});
  };


  handleActive = () => {
    console.log('/home/history/'+email);
    var _this = this;
    var udata = [];
    var email_1 = email.replace("@","%40")
    fetch('/home/history/'+email_1).then(function(res) {
				return res.text()
			}).then(function(body) {
        var s = JSON.parse(body)
              for (var i = 1; i < s.processes.length; i++) {
        var new_s = {};
      new_s.req_no = s.processes[i]._id
      new_s.process = s.processes[i].process;
      new_s.last_updated = s.processes[i].last_updated;
      new_s.status = s.processes[i].status;
      var dt = s.processes[i].req_date.split("/")
      new_s.birthdate =new Date(parseInt(dt[0]), parseInt(dt[1])-1, parseInt(dt[2]))
      new_s.location = s.processes[i].req_loc
      new_s.code = dt[3]
      if (s.processes[i].req_time.length < 6){
        var o=""
        for(var ix =0;ix <s.processes[i].req_time.length;ix++){
            o = o+"0"
        }
        s.processes[i].req_time = o+s.processes[i].req_time
      }
      new_s.hour = parseInt(s.processes[i].req_time.substring(0, 2))
      new_s.min = parseInt(s.processes[i].req_time.substring(2, 4))
      new_s.sec = parseInt(s.processes[i].req_time.substring(4, 6))
      udata.push(new_s)
      }
        _this.setState({source:udata})
        console.log(udata)
		  })

  };

  render () {
    return (
      <section>

        <Tabs index={this.state.fixedIndex} onChange={this.handleFixedTabChange} fixed>
          <Tab label='Weather Forecast'><DatePickerIn /></Tab>
          <Tab label='History' onActive={this.handleActive}><TableTest source={this.state.source} /><SubmitButton
            accent
            raised
            primary
            label = "Refresh"
            onClick={this.handleActive}
          />
           </Tab>
        </Tabs>

      </section>
    );
  }
}





const SearchCards = () => (
	<div className="mdl-grid">
   		<div className="mdl-layout-spacer"></div>
   			<div className="mdl-cell mdl-cell--8-col">
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
