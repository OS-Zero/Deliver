import { useState } from 'react';

interface FormState {
  channelTypes: any[];
  channelProviders: any[];
  messageTypes: any[];
  appIds: any[];
}

export const useFormState = () => {
  const [formState, setFormState] = useState<FormState>({
    channelTypes: [],
    channelProviders: [],
    messageTypes: [],
    appIds: []
  });

  const updateFormState = (newState: Partial<FormState>) => {
    setFormState((prevState) => ({ ...prevState, ...newState }));
  };

  return { formState, updateFormState };
};
