import React, { useMemo } from 'react';
import { useTable } from 'react-table';
import { deleteUser } from '../../api/manage-clients-api.js';

const ClientsTable = ({ data, setClients }) => {
    const columns = useMemo(() => [
        {
            Header: "ID",
            accessor: "id",
        },
        {
            Header: "Imię",
            accessor: "name",
        },
        {
            Header: "Nazwisko",
            accessor: "surname",
        },
        {
            Header: "Email",
            accessor: "email",
        },
        {
            Header: "Nr telefonu",
            accessor: "phone",
        },
        {
            Header: "Akcje",
            Cell: ({ row }) => (
                <button className="disable-button" onClick={() => deleteUser(row.original.id, setClients)}>
                    Usuń
                </button>
            )
        },
    ], [setClients]);

    const { getTableProps, getTableBodyProps, headerGroups, rows, prepareRow } = useTable({ columns, data });

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

export default ClientsTable;
