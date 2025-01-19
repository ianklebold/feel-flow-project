import React from "react";

function Table({
  columns,
  data,
  noDataText = "No hay datos disponibles",
  showIcon = false,
  onIconClick = () => {},
  onRowClick = null, // Nueva prop para manejar clics en filas
}) {
  console.log("Datos recibidos en la tabla:", data);

  return (
    <div className="mt-8">
      <div className="overflow-hidden rounded-lg border-2 border-secondary">
        <table className="min-w-full table-auto shadow-lg border-collapse">
          <thead>
            <tr className="bg-light text-primary">
              {columns.map((col, index) => (
                <th
                  key={index}
                  className="px-4 py-2 border-b border-light text-center"
                >
                  {col.header}
                </th>
              ))}
            </tr>
          </thead>
          <tbody>
            {data.length > 0 ? (
              data.map((row, rowIndex) => (
                <tr
                  key={rowIndex}
                  className={`${
                    rowIndex % 2 === 0 ? "bg-gray-50" : "bg-white"
                  } transition duration-300 hover:bg-gray-100 ${
                    onRowClick ? "cursor-pointer" : ""
                  }`}
                  onClick={() => onRowClick && onRowClick(row)} // Llamar a onRowClick si está definido
                >
                  {columns.map((col, colIndex) => (
                    <td
                      key={colIndex}
                      className="px-4 py-2 border-b border-light text-center"
                    >
                      {col.key === "icon" && showIcon ? (
                        <div className="flex items-center justify-center">
                          <button
                            onClick={(e) => {
                              e.stopPropagation(); // Prevenir que el clic en el botón active el clic en la fila
                              onIconClick(row);
                            }}
                            className="flex items-center"
                          >
                            <img
                              src={row.iconUrl}
                              alt="Icono"
                              className="w-8 h-8 rounded-full border-2 border-gray-300 hover:border-blue-500"
                            />
                          </button>
                        </div>
                      ) : (
                        row[col.key] // Mostrar el contenido de la columna
                      )}
                    </td>
                  ))}
                </tr>
              ))
            ) : (
              <tr>
                <td
                  colSpan={columns.length}
                  className="px-4 py-2 text-center border-b border-light"
                >
                  {noDataText}
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default Table;
