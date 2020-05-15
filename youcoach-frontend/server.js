const express = require('express');
let cors = require('cors');
let os = require('os');
const app = express();

app.use(express.static('./dist/youcoach'));

app.listen(process.env.PORT || 8080);

