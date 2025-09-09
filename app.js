const express = require('express');
const app = express();
const port = 3000;


app.get('/', (req, res) => {
  res.send('Good Morning, Vietnam');
});

app.listen(port, () => {
  console.log('App runninign on http://localhost:${port}');
});
