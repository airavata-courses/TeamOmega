import json
import CreateLink
from nose.tools import assert_equal

app = CreateLink.app.test_client()

def test_root():
    response = app.get('/')

    assert_equal(response.status_code, 200)
    assert_equal(response.data, "A REST-API for creating a link for Nexrad level 2 scan files on AWS")

def test_getlink_success():
    expected={"link": "https://noaa-nexrad-level2.s3.amazonaws.com/2016/09/06/DAN1/DAN120160906_122059_V06.gz"}
    response = app.get('/getlink/DAN109062016122059')

    assert_equal(response.status_code, 200)
    link = json.loads(response.data)['link']
    assert_equal(link,expected['link'])

def test_getlink_error():
    expected = "Something went wrong with the given input parameters, link cannot be created"
    response = app.get('/getlink/DAN10906201612205')

    assert_equal(response.status_code, 200)
    link = json.loads(response.data)['error']
    assert_equal(link,expected)