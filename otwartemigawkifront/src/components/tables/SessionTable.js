import React, { useMemo } from 'react';
import { useTable } from 'react-table';

const SessionTable = ({ data, handleToggle }) => {
  const columns = useMemo(() => [
    {
      Header: "ID",
      accessor: "sessionTypeId",
    },
    {
      Header: "Nazwa sesji",
      accessor: "sessionTypeName",
    },
    {
      Header: "Cena",
      accessor: "price",
    },
    {
      Header: "Dezaktywowana",
      accessor: "disabled",
      Cell: ({ value }) => (value ? 'Tak' : 'Nie'),
    },
    {
      Header: "Akcje",
      Cell: ({ row }) => {
        const isDisabled = row.original.disabled;
        return (
          <button className={isDisabled ? 'enable-button' : 'disable-button'}
            onClick={() => handleToggle(row.original.sessionTypeId)}>
            {isDisabled ? 'Aktywuj' : 'Dezaktywuj'}
          </button>
        );
      },
    },
  ], [handleToggle]);

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

export default SessionTable;
