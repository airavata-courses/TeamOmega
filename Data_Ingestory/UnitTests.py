import json
import GetNexradList
from nose.tools import assert_equal

app = GetNexradList.app.test_client()

def test_root():
    response = app.get('/')

    assert_equal(response.status_code, 200)
    assert_equal(response.data, "REST API for getting the files from AWS Nexrad")

def test_getlink_success():
    expected={"link": ["https://noaa-nexrad-level2.s3.amazonaws.com/2015/12/05/KIND/KIND20151205_002046_V06.gz"]}
    response = app.get('/getpath?year=2015&month=12&day=05&station=KIND&time=002046')

    assert_equal(response.status_code, 200)
    link = json.loads(response.data)['link']
    assert_equal(link,expected['link'])

def test_getlink_error():
    expected = "Nothing exists matching the given parameters"
    response = app.get('/getpath?year=2015&month=12&day=05&station=KIND&time=002040')

    assert_equal(response.status_code, 200)
    link = json.loads(response.data)['error']
    assert_equal(link,expected)
