import React, { useMemo } from 'react';
import { useTable } from 'react-table';

const UpcomingSessionsTable = ({ data, handleDeleteSession }) => {
  const columns = useMemo(() => [
    {
      Header: "ID",
      accessor: "id",
    },
    {
      Header: "Nazwa sesji",
      accessor: "sessionTypeName",
    },
    {
      Header: "Data",
      accessor: "date",
    },
    {
      Header: "Imię",
      accessor: "clientName",
    },
    {
      Header: "Nazwisko",
      accessor: "clientSurname",
    },
    {
      Header: "Nr telefonu",
      accessor: "clientPhone",
    },
    {
      Header: "Akcje",
      Cell: ({ row }) => (
        <button className='disable-button' onClick={() => handleDeleteSession(row.original.id)}>Odwołaj sesję</button>
      )
    },
  ], [handleDeleteSession]);

  const {
    getTableProps,
    getTableBodyProps,
    headerGroups,
    rows,
    prepareRow,
  } = useTable({
    columns,
    data,
  });

  return (
    <table {...getTableProps()}>
      <thead>
        {headerGroups.map((headerGroup) => (
          <tr {...headerGroup.getHeaderGroupProps()}>
            {headerGroup.headers.map((column) => (
              <th {...column.getHeaderProps()}>
                {column.render("Header")}
              </th>
            ))}
          </tr>
        ))}
      </thead>
      <tbody {...getTableBodyProps()}>
        {rows.map((row) => {
          prepareRow(row);
          return (
            <tr {...row.getRowProps()}>
              {row.cells.map((cell) => (
                <td {...cell.getCellProps()}>
                  {cell.render("Cell")}
                </td>
              ))}
            </tr>
          );
        })}
      </tbody>
    </table>
  );
};

export default UpcomingSessionsTable;
