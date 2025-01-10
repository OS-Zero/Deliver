import React, { createContext, useState, useContext, ReactNode } from 'react';

interface GlobalContextType {
  token: string | null;
  setToken: (token: string | null) => void;
  currentGroup: { id: number; name: string } | null;
  setCurrentGroup: (group: { id: number; name: string } | null) => void;
}

const GlobalContext = createContext<GlobalContextType | undefined>(undefined);

export const GlobalProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
  const [token, setToken] = useState<string | null>(null);
  const [currentGroup, setCurrentGroup] = useState<{ id: number; name: string } | null>(null);

  return (
    <GlobalContext.Provider value={{ token, setToken, currentGroup, setCurrentGroup }}>
      {children}
    </GlobalContext.Provider>
  );
};

export const useGlobalContext = () => {
  const context = useContext(GlobalContext);
  if (context === undefined) {
    throw new Error('useGlobalContext must be used within a GlobalProvider');
  }
  return context;
};
