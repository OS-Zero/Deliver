import { createRoot } from 'react-dom/client';
import '@/styles/index.module.scss';
import { BrowserRouter } from 'react-router-dom';
import App from './app';

createRoot(document.getElementById('root')!).render(
  <BrowserRouter>
    <App />
  </BrowserRouter>
);
