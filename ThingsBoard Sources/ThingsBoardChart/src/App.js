import React, { useState, useEffect } from 'react';
import { LineChart, CartesianGrid, Line, XAxis, YAxis } from 'recharts'
import axios from 'axios'

const jwt = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlbmcubW9oYW1lZGF0ZWY5OEBnbWFpbC5jb20iLCJzY29wZXMiOlsiVEVOQU5UX0FETUlOIl0sInVzZXJJZCI6ImMyOGNkMzAwLWE4ZDktMTFlYS04ZDUwLTQzODYwYTNjYjJjOSIsImZpcnN0TmFtZSI6Ik1vaGFtZWQiLCJsYXN0TmFtZSI6IkF0ZWYiLCJlbmFibGVkIjp0cnVlLCJwcml2YWN5UG9saWN5QWNjZXB0ZWQiOnRydWUsImlzUHVibGljIjpmYWxzZSwidGVuYW50SWQiOiJjMjdkMWI5MC1hOGQ5LTExZWEtOGQ1MC00Mzg2MGEzY2IyYzkiLCJjdXN0b21lcklkIjoiMTM4MTQwMDAtMWRkMi0xMWIyLTgwODAtODA4MDgwODA4MDgwIiwiaXNzIjoidGhpbmdzYm9hcmQuaW8iLCJpYXQiOjE1OTI4Njk1MjEsImV4cCI6MTU5NDY2OTUyMX0.jbGGq23-Vp_qGzPOsspqUqQgeC1b0qEQ6faNcVCY8C2UJklyhz6mDh5WRXVFDZnm_2Phe3xzYSF4PoqIE8HQJQ'
const deviceID = 'cda602c0-a8d9-11ea-8d50-43860a3cb2c9'

const veryHistoricDateTs = '1479735870785'
const veryFuturisticDateTs = '1679735871858'

const App = () => {
  const [temperatures, setTemperatures] = useState([])

  useEffect(() => {
    let interval = setInterval(() => {
      axios.get(`https://demo.thingsboard.io/api/plugins/telemetry/DEVICE/${deviceID}/values/timeseries?keys=temperature&startTs=${veryHistoricDateTs}&endTs=${veryFuturisticDateTs}`, {
        headers: {
          'X-Authorization': `Bearer ${jwt}`
        }
      })
        .then(res => setTemperatures(res.data.temperature))
    }, 1000)

    return () => clearInterval(interval)
  }, [])
  return (
    <div className="App">
      <header className="App-header">
        <LineChart width={900} height={600} data={temperatures}>
          <Line type="monotone" dataKey="value" stroke="#8884d8" />
          <CartesianGrid stroke="#ccc" />
          <XAxis dataKey="ts" />
          <YAxis />
        </LineChart>
      </header>
    </div>
  )
}


export default App;
