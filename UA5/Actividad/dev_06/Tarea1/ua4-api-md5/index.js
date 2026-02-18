const express = require('express');
const crypto = require('crypto');

const app = express();
const port = process.env.PORT || 3000;

const API_NAME = process.env.API_NAME || 'My API Name';
const API_VERSION = process.env.API_VERSION || '1.0.0';

app.get('/api/hash', (req, res) => {
  const startTime = process.hrtime.bigint();
  const { algorithm, text } = req.query;

  if (!algorithm) {
    return res.status(400).json({
      header: {
        api_name: API_NAME,
        api_version: API_VERSION,
        api_user: 'guest',
        api_endpoint: 'api/hash/',
        http_request_method: 'GET',
        http_request_parameters: {
          algorithm: null,
          text: text || null
        },
        http_response_status: 400,
        http_response_message: 'Bad Request',
        response_time: Number(process.hrtime.bigint() - startTime)
      },
      body: {
        error: 'El parámetro "algorithm" es obligatorio.'
      }
    });
  }

  if (algorithm.toLowerCase() !== 'md5') {
    return res.status(400).json({
      header: {
        api_name: API_NAME,
        api_version: API_VERSION,
        api_user: 'guest',
        api_endpoint: 'api/hash/',
        http_request_method: 'GET',
        http_request_parameters: {
          algorithm,
          text: text || null
        },
        http_response_status: 400,
        http_response_message: 'Bad Request',
        response_time: Number(process.hrtime.bigint() - startTime)
      },
      body: {
        error: 'Solo se permite algorithm=md5.'
      }
    });
  }

  if (typeof text !== 'string' || text.length === 0) {
    return res.status(400).json({
      header: {
        api_name: API_NAME,
        api_version: API_VERSION,
        api_user: 'guest',
        api_endpoint: 'api/hash/',
        http_request_method: 'GET',
        http_request_parameters: {
          algorithm,
          text: text || null
        },
        http_response_status: 400,
        http_response_message: 'Bad Request',
        response_time: Number(process.hrtime.bigint() - startTime)
      },
      body: {
        error: 'El parámetro "text" es obligatorio.'
      }
    });
  }

  const hash = crypto.createHash('md5').update(text, 'utf8').digest('hex');
  const responseTime = Number(process.hrtime.bigint() - startTime);

  return res.status(200).json({
    header: {
      api_name: API_NAME,
      api_version: API_VERSION,
      api_user: 'guest',
      api_endpoint: 'api/hash/',
      http_request_method: 'GET',
      http_request_parameters: {
        algorithm: 'md5',
        text
      },
      http_response_status: 200,
      http_response_message: 'OK',
      response_time: responseTime
    },
    body: {
      algorithm: 'md5',
      text,
      hash
    }
  });
});

app.get('/', (_, res) => {
  res.json({
    message: 'API MD5 activa. Usa GET /api/hash?algorithm=md5&text=tu_texto'
  });
});

app.listen(port, () => {
  console.log(`API escuchando en http://localhost:${port}`);
});
