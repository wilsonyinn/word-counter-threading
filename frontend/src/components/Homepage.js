import React, { useState } from 'react';

function App() {
  const [file, setFile] = useState(null);
  const [number, setNumber] = useState('');

  const handleFileChange = (event) => {
    const selectedFile = event.target.files[0];
    setFile(selectedFile);
  };

  const handleNumberChange = (event) => {
    const inputNumber = event.target.value;
    setNumber(inputNumber);
  };

  const handleSubmit = () => {
    // You can perform actions with 'file' and 'number' here, such as uploading the file or processing the number
    console.log('Uploaded file:', file);
    console.log('Number:', number);
    // Reset the state after submission if necessary
    setFile(null);
    setNumber('');
  };

  return (
    <div>
      <input type="file" accept=".txt" onChange={handleFileChange} />
      <br />
      <input
        type="text"
        placeholder="Enter a number"
        value={number}
        onChange={handleNumberChange}
      />
      <br />
      <button onClick={handleSubmit}>Submit</button>
    </div>
  );
}

export default App;
