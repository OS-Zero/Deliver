import { useState } from 'react';

interface FormState {
  channelTypes: any[];
  channelProviders: any[];
  messageTypes: any[];
  appIds: any[];
  isChannelTypeDisabled: boolean;
}

export const useFormState = () => {
  const [formState, setFormState] = useState<FormState>({
    channelTypes: [],
    channelProviders: [],
    messageTypes: [],
    appIds: [],
    isChannelTypeDisabled: true
  });

  const updateFormState = (newState: Partial<FormState>) => {
    setFormState((prevState) => ({ ...prevState, ...newState }));
  };

  return { formState, updateFormState };
};
