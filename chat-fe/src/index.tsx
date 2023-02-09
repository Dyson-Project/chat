import React from 'react';
import ReactDOM from 'react-dom/client';
import './assets/scss/index.css';
import NewApp from "./NewApp";

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(
  <React.StrictMode>
    <NewApp/>
  </React.StrictMode>
);
