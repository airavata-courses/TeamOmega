from boto import connect_s3
import json

class DataIngestor(object):
    """docstring for DataIngestor"""
    def __init__(self): 
        self.s3conn = None
        self.bucket = None
        self.json_data = None
        
    def start(self):
        self.connect_s3()
        self.load_jsonData()

    def connect_s3(self):
        self.s3conn = connect_s3(anon=True)
        self.bucket = self.s3conn.get_bucket('noaa-nexrad-level2',validate=False)
   
    def load_jsonData(self):
        with open('data.json', 'r') as data_file:    
            self.json_data = json.load(data_file)

    
    def get_stationlist(self, root_prefix = '2011/06/05/', type=3):
        station_list = []
        for key in self.bucket.list(prefix=root_prefix, delimiter='/'):
            key_name = key.name.encode('utf-8')
            if key_name != 'index.html' and len(key_name)<=42:
                if type == 3:
                    station_code = key_name.split('/')[type]
                if (type ==3 and len(station_code) == 4) or (type ==4 ):
                    station_list.append(station_code)
        return station_list

    def parse_json(self, station_list):
        station_data = {}
        for station in station_list:
            try:
                slist = self.json_data[station]
                slist[3] = (" ".join(slist[3].split("_"))).title()
                station_data[station]=slist
            except Exception, e:
                print e
        return station_data

    def timeparse(self,loc_url, timest=120000):

        time_list = self.get_stationlist(root_prefix=loc_url, type =4)
        time_list = [(int(item.split("_")[1]),item) for item in time_list]
        
        return min(time_list, key=lambda x:abs(x[0]-timest))[1]

    
if __name__ == '__main__':
    
    d = DataIngestor()   

    d.start()

    # s_list= d.get_stationlist(root_prefix = '2011/06/05/', type=3)

    # print d.parse_json(s_list)
    print d.timeparse('2011/06/05/KBOX/')


