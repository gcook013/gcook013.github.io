from pymongo import MongoClient
from bson.objectid import ObjectId

class Database(object):
    """ CRUD operations for Animal collection in MongoDB """

    def __init__(self):
        # Initializing the MongoClient. This helps to
        # access the MongoDB databases and collections.
        self.client = MongoClient(host='localhost', port=27017)
        self.database = self.client['testDB']

# Complete this create method to implement the C in CRUD.
    def create(self, data):
        # if data is present use insert function for data
        if data is not None:
            self.database.user.insert_one(data)  # data should be dictionary
            return True
        else:
            raise Exception("Nothing to save, because data parameter is empty")
            return False

# Create method to implement the R in CRUD.
    def read(self, data):
        # if data is present use find function for data
        if data is not None:
            results = self.database.user.find(data, {"_id":False}) # data must be dictionary
            # for result in results:
            #     print(result)
            return results
        else:
            raise Exception("Nothing to find, because data parameter is empty")
            return False

# Create method to implement the U in CRUD
    def update(self, query, data):
        # if data is present update record found using query paramater passed
        if data is not None:
            result = self.database.user.update_one(query, data) # both query and data must be dictionary
            return result.raw_result
        else:
            raise Exception("Nothing to update, because data parameter is empty")

# Create method to implement the D in CRUD
    def delete(self, data):
        # if data is present update record found using query parameter passed
        if data is not None:
            result = self.database.user.delete_one(data) # data must be dictionary
            return result.raw_result
        else:
            raise Exception("Nothing to update, because data parameter is empty")
