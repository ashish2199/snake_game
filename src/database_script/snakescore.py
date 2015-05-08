#!/usr/bin/env python

import MySQLdb,cgi, cgitb 
params = cgi.FieldStorage() 
name = params.getvalue('name') 
score = params.getvalue('score')
print "Content-type: text/html"
print


if name: isql = "INSERT INTO scores VALUES("+"'"+name+"',"+score+")"

db = MySQLdb.connect("fdb13.runhosting.com","1807469_mydb","ashish2199","1807469_mydb" )

cursor = db.cursor()
if name:cursor.execute(isql)

sql="SELECT * FROM scores"
cursor.execute(sql)
data = cursor.fetchall()



for row in data:
        print " %s " % row[0]
        print " %s " % row[1]
        
        



db.close()
