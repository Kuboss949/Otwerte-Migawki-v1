// DropzoneComponent.js

import React, { useCallback, useEffect } from 'react';
import { useDropzone } from 'react-dropzone';

function DropzoneComponent({ uploadedFiles, setUploadedFiles }) {
  const onDrop = useCallback((acceptedFiles) => {
    setUploadedFiles((prevFiles) => [
      ...prevFiles,
      ...acceptedFiles.map((file) => Object.assign(file, { preview: URL.createObjectURL(file) }))
    ]);
  }, [setUploadedFiles]);

  const deleteFile = (index) => {
    setUploadedFiles((prevFiles) => {
      const newFiles = prevFiles.filter((_, i) => i !== index);
      URL.revokeObjectURL(prevFiles[index].preview); // Clean up object URL
      return newFiles;
    });
  };

  useEffect(() => {
    // Cleanup the object URLs when component unmounts
    return () => {
      uploadedFiles.forEach((file) => URL.revokeObjectURL(file.preview));
    };
  }, [uploadedFiles]);

  const { getRootProps, getInputProps } = useDropzone({ onDrop, multiple: true });

  return (
    <div className='dropzone-outer-div'>
      <div {...getRootProps({ className: "dropzone" })}>
        <input className="input-zone" {...getInputProps()} />
        <div>Przeciągnij i upuść obrazy tutaj lub kliknij, aby wybrać pliki</div>
      </div>
      <div className='preview-img-div'>
        {uploadedFiles.map((file, index) => (
          <div key={index} className='preview-container'>
            <img
              src={file.preview}
              alt={`Preview ${index}`}
              className='preview-img'
            />
            <button className='dropzone-preview-delete' onClick={() => deleteFile(index)}>X</button>
          </div>
        ))}
      </div>
    </div>
  );
}

export default DropzoneComponent;
