import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPlaneacionActividad, defaultValue } from 'app/shared/model/planeacion-actividad.model';

export const ACTION_TYPES = {
  FETCH_PLANEACIONACTIVIDAD_LIST: 'planeacionActividad/FETCH_PLANEACIONACTIVIDAD_LIST',
  FETCH_PLANEACIONACTIVIDAD: 'planeacionActividad/FETCH_PLANEACIONACTIVIDAD',
  CREATE_PLANEACIONACTIVIDAD: 'planeacionActividad/CREATE_PLANEACIONACTIVIDAD',
  UPDATE_PLANEACIONACTIVIDAD: 'planeacionActividad/UPDATE_PLANEACIONACTIVIDAD',
  DELETE_PLANEACIONACTIVIDAD: 'planeacionActividad/DELETE_PLANEACIONACTIVIDAD',
  RESET: 'planeacionActividad/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPlaneacionActividad>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type PlaneacionActividadState = Readonly<typeof initialState>;

// Reducer

export default (state: PlaneacionActividadState = initialState, action): PlaneacionActividadState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PLANEACIONACTIVIDAD_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PLANEACIONACTIVIDAD):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PLANEACIONACTIVIDAD):
    case REQUEST(ACTION_TYPES.UPDATE_PLANEACIONACTIVIDAD):
    case REQUEST(ACTION_TYPES.DELETE_PLANEACIONACTIVIDAD):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PLANEACIONACTIVIDAD_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PLANEACIONACTIVIDAD):
    case FAILURE(ACTION_TYPES.CREATE_PLANEACIONACTIVIDAD):
    case FAILURE(ACTION_TYPES.UPDATE_PLANEACIONACTIVIDAD):
    case FAILURE(ACTION_TYPES.DELETE_PLANEACIONACTIVIDAD):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PLANEACIONACTIVIDAD_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PLANEACIONACTIVIDAD):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PLANEACIONACTIVIDAD):
    case SUCCESS(ACTION_TYPES.UPDATE_PLANEACIONACTIVIDAD):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PLANEACIONACTIVIDAD):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/planeacion-actividads';

// Actions

export const getEntities: ICrudGetAllAction<IPlaneacionActividad> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PLANEACIONACTIVIDAD_LIST,
  payload: axios.get<IPlaneacionActividad>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IPlaneacionActividad> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PLANEACIONACTIVIDAD,
    payload: axios.get<IPlaneacionActividad>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IPlaneacionActividad> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PLANEACIONACTIVIDAD,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPlaneacionActividad> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PLANEACIONACTIVIDAD,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPlaneacionActividad> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PLANEACIONACTIVIDAD,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
