import React from "react";

const IconButton = ({ icon: Icon, onClick, color = "blue", size = "md", tooltip = "", id }) => {
    const sizeClasses = {
        sm: "p-2 text-sm",
        md: "p-3 text-base",
        lg: "p-4 text-lg",
    };

    const colorClasses = {
        blue: "text-textPrimary hover:bg-bgBluePrimary",
        black: "text-black hover:bg-bgBlueSecondary",
        red: "text-red-500 hover:bg-red-100",
        green: "text-green-500 hover:bg-green-100",
        gray: "text-gray-500 hover:bg-gray-100",
    };

    return (
        <button
            onClick={onClick}
            className={`flex items-center justify-center rounded-full ${sizeClasses[size]} ${colorClasses[color]} transition-all duration-200`}
            aria-label={tooltip}
            title={tooltip}
            id={id}
        >
            <Icon className="w-5 h-5" />
        </button>
    );
};

export default IconButton;
