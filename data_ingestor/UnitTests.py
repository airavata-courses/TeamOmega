from nose.tools import assert_equal
import app
import json
test_app = app.app.test_client()

def test_start():
	response = test_app.get('/')
	assert_equal(response.status_code, 200)
	assert_equal(response.data, "<h1> Hello, Data Ingestor is running</h1>")



def test_location():
	response = test_app.post('/get_loc', data=json.dumps({'date': '2016/09/20/', 
	'timest': '2016-10-21T03:13:38.994Z', 
	'room': 'songires@gmail.com-1db419e2741b55ce',
        'hostIp': '127.0.0.1'
	}), content_type='application/json')
	json_resp = json.loads(response.get_data())
	assert_equal("Station codes from dataingestor", json_resp.get("msg"))

		
def test_url():
	response = test_app.post('/get_url', data=json.dumps({'date': '2016/09/20/KBOX/', 
	'timest': '050612', 
	'room': 'songires@gmail.com-1db419e2741b55ce',
        'hostIp': '127.0.0.1'
	}), content_type='application/json')
	json_resp = json.loads(response.get_data())
	assert_equal(response.status_code, 200)
	assert_equal("Data added to the queue..", json_resp.get("msg"))
		
