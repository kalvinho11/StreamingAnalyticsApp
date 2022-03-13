print("------------------------------------CREATING NEW USER AND DATABASE------------------------------------")
db = db.getSiblingDB('analytics')
db.createUser({
  user: 'appUser',
  pwd: 'password',
  roles: [
    {
      role: 'readWrite',
      db: 'analytics',
    },
  ],
});