import re
from flask import Flask, render_template, request
import json
import crud

# create flask app
app = Flask(__name__)

# use crud module to create new Database class object
db = crud.Database()

# base routes
@app.route("/")
@app.route("/home")

# home function to define root html
def home():
    return render_template("index.html")

# create function to call create method from crud class
@app.route("/create", methods = ['POST', 'GET'])
def create():
    
    # get information from html form input fields
    output = request.form.to_dict()
    
    # pass argument to create method from crud class
    db.create(output)
    
    # return information for record created and display to user
    data="Record: " + "{userName} {userID}".format(**output) + " created."
    return render_template("results.html", data = data)

@app.route("/read", methods = ['POST', 'GET'])
def read():
    #get information from html form input fields
    output = request.form.to_dict()

    # pass argument to read method from crud class
    result = db.read(output)
    
    # iterate through cursor returned and populate list    
    dataList = []
    for doc in result:
        dataList.append(str(doc))

    # convert list to string and display records read to user
    data=str(dataList)
    return render_template("results.html", data = data)

@app.route("/update", methods = ['POST', 'GET'])
def update():

    # hard coded query and data to update
    queryData = {"userID": "10"}
    
    updateData = {"$set": {"userName": "UserName"}}
    
    # pass arguments to update methods from crud class and display update result to user
    result = db.update(queryData, updateData)    
    return render_template("results.html", data = result)

@app.route("/delete", methods = ['POST', 'GET'])
def delete():
    #get information from html form input fields
    output = request.form.to_dict()

    # pass argument to delete method from crud class and display deletion result to user
    result = db.delete(output)
    return render_template("results.html", data = result)


if __name__ == '__main__':
    app.run(debug=True,port=5001)


